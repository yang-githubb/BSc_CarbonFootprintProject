import sys
import json
import urllib.parse
from sklearn.feature_extraction.text import TfidfVectorizer
from sklearn.cluster import KMeans
import traceback
import random
from sklearn.metrics.pairwise import cosine_similarity;
import numpy as np;

try:
    def read_json_file(file_path):
        with open(file_path, 'r') as file:
            return json.load(file)
        
    def read_string_file(file_path):
        with open(file_path, 'r') as file:
            json_string = file.read()
            return json.loads(json_string)
        
    def extract_actions_by_category(data, category):
        return [action['action_name'] for action in data if action['action_category'] == category]

    def cluster_solutions(solutions, n_clusters=3):
        vectorizer = TfidfVectorizer()
        X = vectorizer.fit_transform(solutions)
        kmeans = KMeans(n_clusters=n_clusters)
        kmeans.fit(X)
        return kmeans, vectorizer

    def recommend_actions(question, cluster_label, actions, vectorizer, kmeans_model, top_n=2):
        question_vec = vectorizer.transform([question])
        predicted_cluster = kmeans_model.predict(question_vec)[0]
        
        if predicted_cluster == cluster_label:
            action_vecs = vectorizer.transform(actions)
            similarities = cosine_similarity(question_vec, action_vecs)[0]
            
            sorted_actions_indices = np.argsort(similarities)[::-1]
            
            top_actions_indices = sorted_actions_indices[:top_n]
            recommended_actions = [actions[i] for i in top_actions_indices]
            
            return recommended_actions
        else:
            return None


    def predict_cluster(question, kmeans_model, vectorizer):
        question_vec = vectorizer.transform([question])
        predicted_cluster = kmeans_model.predict(question_vec)[0]
        return predicted_cluster
    
    def extract_actionsnameanddesc_by_category(data):
        return [(action['action_name'], action['action_description']) for action in data]


    if __name__ == "__main__":
        json_data = read_json_file('temp_json_data.json')
        question = read_string_file('temp_user_questions.json')        

        home_actions = extract_actions_by_category(json_data, 'home')
        transport_actions = extract_actions_by_category(json_data, 'Transportation')
        waste_actions = extract_actions_by_category(json_data, 'waste')
        
        all_action = extract_actionsnameanddesc_by_category(json_data)

        if home_actions:
            km_home, vectorizer_home = cluster_solutions(home_actions)
        if transport_actions:
            km_transport, vectorizer_transport = cluster_solutions(transport_actions)
        if waste_actions:
            km_waste, vectorizer_waste = cluster_solutions(waste_actions)

        food_list = []
        house_list = []
        transportation_list = []
        for ques, category in question.items():
            if category.lower() == "waste":
                food_list.append(ques)
            elif category.lower() == "home":
                house_list.append(ques)
            elif category.lower() == "transportation":
                transportation_list.append(ques)
                
        recommendations = {}
        if house_list:
            home_inf = vectorizer_home.transform(house_list)
            predicted_home_label = km_home.predict(home_inf)[0]
            cluster_label = predicted_home_label
            actions = home_actions
            vectorizer = vectorizer_home
            kmeans_model = km_home
            for question_text in house_list:
                recommended_actions = recommend_actions(question_text, cluster_label, actions, vectorizer, kmeans_model)
                recommendations[question_text] = recommended_actions

        if food_list:
            waste_inf = vectorizer_waste.transform(food_list)
            predicted_waste_label = km_waste.predict(waste_inf)[0]
            cluster_label = predicted_waste_label
            actions = waste_actions
            vectorizer = vectorizer_waste
            kmeans_model = km_waste
            for question_text in food_list:
                recommended_actions = recommend_actions(question_text, cluster_label, actions, vectorizer, kmeans_model)
                recommendations[question_text] = recommended_actions
                
        if transportation_list:
            transport_inf = vectorizer_transport.transform(transportation_list)
            predicted_transport_label = km_transport.predict(transport_inf)[0]
            cluster_label = predicted_transport_label
            actions = transport_actions
            vectorizer = vectorizer_transport
            kmeans_model = km_transport
            for question_text in transportation_list:
                recommended_actions = recommend_actions(question_text, cluster_label, actions, vectorizer, kmeans_model)
                recommendations[question_text] = recommended_actions
                            
        recommendation_list = []
        for recommended_actname in recommendations.values():
            if recommended_actname is not None: 
                for action_name, action_description in all_action:
                    if action_name in recommended_actname:
                        recommendation_list.append((action_name, action_description))
            else:
                    random_action = random.choice(all_action) 
                    recommendation_list.append((random_action[0], random_action[1]))        
        
        print(set(recommendation_list))
except Exception as e:
    error_message = str(e)
    error_traceback = traceback.format_exc()
    print("An error occurred: ", error_message)
    print("Traceback details:")
    print(error_traceback)
