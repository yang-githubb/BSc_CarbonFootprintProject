"""Recommends carbon-reduction actions for a user's flagged survey questions.

Usage: python clusterAction.py <user_questions.json> <actions.json>

The first file maps flagged question text to its category ("home", "waste",
"Transportation"); the second is the actions table exported as JSON. Actions
in each category are clustered (TF-IDF + KMeans); for each flagged question
the actions in the question's predicted cluster are ranked by cosine
similarity and the best matches are returned.

Output: a JSON array of {"name": ..., "description": ...} objects.
"""

import json
import sys

import numpy as np
from sklearn.cluster import KMeans
from sklearn.feature_extraction.text import TfidfVectorizer
from sklearn.metrics.pairwise import cosine_similarity

TOP_N = 2
MAX_CLUSTERS = 3
RANDOM_STATE = 42



def read_json_file(file_path):
    with open(file_path, "r", encoding="utf-8") as file:
        return json.load(file)


def cluster_actions(action_names):
    vectorizer = TfidfVectorizer()
    vectors = vectorizer.fit_transform(action_names)
    n_clusters = min(MAX_CLUSTERS, len(action_names))
    kmeans = KMeans(n_clusters=n_clusters, random_state=RANDOM_STATE, n_init=10)
    labels = kmeans.fit_predict(vectors)
    return vectorizer, kmeans, labels, vectors


def recommend_for_question(question, vectorizer, kmeans, labels, vectors, action_names):
    """Ranks the actions in the question's predicted cluster by similarity."""
    question_vec = vectorizer.transform([question])
    predicted_cluster = kmeans.predict(question_vec)[0]

    cluster_indices = [i for i, label in enumerate(labels) if label == predicted_cluster]
    if not cluster_indices:
        cluster_indices = list(range(len(action_names)))

    similarities = cosine_similarity(question_vec, vectors[cluster_indices])[0]
    ranked = np.argsort(similarities)[::-1][:TOP_N]
    return [action_names[cluster_indices[i]] for i in ranked]


def main():
    user_questions_path = sys.argv[1] if len(sys.argv) > 1 else "temp_user_questions.json"
    actions_path = sys.argv[2] if len(sys.argv) > 2 else "temp_json_data.json"

    flagged_questions = read_json_file(user_questions_path)
    actions = read_json_file(actions_path)

    descriptions = {action["action_name"]: action["action_description"] for action in actions}
    # Category values in the database are inconsistently cased ("waste" and
    # "Waste"), so group and look up case-insensitively.
    actions_by_category = {}
    for action in actions:
        actions_by_category.setdefault(action["action_category"].lower(), []).append(action["action_name"])

    models = {
        category: cluster_actions(names)
        for category, names in actions_by_category.items()
        if names
    }

    recommended_names = []
    for question, category in flagged_questions.items():
        db_category = category.lower()
        if db_category not in models:
            continue
        vectorizer, kmeans, labels, vectors = models[db_category]
        recommended_names.extend(
            recommend_for_question(
                question, vectorizer, kmeans, labels, vectors, actions_by_category[db_category]
            )
        )

    # Dedupe while preserving ranking order.
    unique_names = list(dict.fromkeys(recommended_names))
    recommendations = [
        {"name": name, "description": descriptions[name]} for name in unique_names
    ]
    print(json.dumps(recommendations))


if __name__ == "__main__":
    try:
        main()
    except Exception as exc:  # noqa: BLE001 - surface errors to the PHP caller
        print(json.dumps({"error": str(exc)}))
        sys.exit(1)
