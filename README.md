# Carbon Footprint Analyser

The Carbon Footprint Analyser app is designed to raise awareness and educate users on their carbon footprint. By providing personalized feedback and actionable insights, the app aims to encourage sustainable living choices.

## Features

- **User Authentication**: Secure login and sign-up functionalities to personalize the experience.
- **Carbon Footprint Survey**: An interactive survey to estimate the user's carbon footprint based on daily habits.
- **Carbon Footprint Facts**: Educational section with facts about carbon footprint and its impact on the environment.
- **User Profile**: Customizable user profiles to track and visualize carbon footprint emission.
- **Actionable Insights**: Suggestions and tips to reduce carbon footprint based on user's survey responses.

## Setup and Running the App

### Prerequisites

- Android Studio
- JDK 8 or newer

### Installation

1. Clone the repository to your local machine.
2. Open the project in Android Studio.
3. Sync the project with Gradle.
4. Run the app on an emulator or physical device.

### Dependencies

- Android SDK v21 or newer
- A PHP + MySQL server (e.g. XAMPP) hosting the files in `Database/`
- Python 3 with `scikit-learn` and `numpy` on the server, for the recommender (`Database/clusterAction.py`)

### Backend Configuration

1. Copy the `Database/` PHP files into your web server (e.g. `htdocs/CarbonFootprintFYP/`).
2. Import `Database/finalDB_carbonfootprint.sql` into MySQL to create the `carbonfootprint` database. If you already have an older database, run `Database/migrations/001_add_api_token.sql` to add the API token column.
3. Set your MySQL credentials in `Database/DataBaseConfig.php`.
4. Point the app at your server by editing `BASE_URL` in `app/src/main/java/com/example/carbonfootprint/ApiConfig.java`.

### API

`login.php` returns a JSON response containing an API token; all other endpoints (`carbCalc.php`, `clusterCalc.php`, `getAction.php`, `submit_answers.php`) require that token and identify the user from it. Survey answers are submitted in one batch to `submit_answers.php` and saved in a single transaction.

Note: traffic is plain HTTP intended for a local development network. Put the backend behind HTTPS before using it outside a lab setup.

## Contributing

### Author: Ooi Yeuan Yang
Contributions to improve the app and expand its functionality are more than welcome. Please submit pull requests for any enhancements.

## Acknowledgments

Special thanks to the Dr Zila who provided insights and verified the information presented in the app.
