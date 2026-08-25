<?php
require "DataBaseConfig.php";

class DataBase
{
    public $connect;
    protected $servername;
    protected $username;
    protected $password;
    protected $databasename;

    public function __construct()
    {
        $this->connect = null;
        $dbc = new DataBaseConfig();
        $this->servername = $dbc->servername;
        $this->username = $dbc->username;
        $this->password = $dbc->password;
        $this->databasename = $dbc->databasename;
    }

    function dbConnect()
    {
        $this->connect = mysqli_connect($this->servername, $this->username, $this->password, $this->databasename);
        return $this->connect;
    }

    /**
     * Verifies the credentials. On success stores a fresh API token and
     * returns ['userId' => int, 'token' => string, 'hasAnswers' => bool];
     * returns null on bad credentials.
     */
    function logIn($username, $password)
    {
        $stmt = $this->connect->prepare("SELECT id, password FROM users WHERE username = ?");
        $stmt->bind_param("s", $username);
        $stmt->execute();
        $row = $stmt->get_result()->fetch_assoc();

        if (!$row || !password_verify($password, $row['password'])) {
            return null;
        }

        $userId = (int) $row['id'];
        $token = bin2hex(random_bytes(32));
        $stmt = $this->connect->prepare("UPDATE users SET api_token = ? WHERE id = ?");
        $stmt->bind_param("si", $token, $userId);
        $stmt->execute();

        return [
            'userId' => $userId,
            'token' => $token,
            'hasAnswers' => $this->userHasAnswers($userId),
        ];
    }

    function signUp($email, $username, $password)
    {
        $stmt = $this->connect->prepare("INSERT INTO users (email, username, password) VALUES (?, ?, ?)");
        $hashed_password = password_hash($password, PASSWORD_DEFAULT);
        $stmt->bind_param("sss", $email, $username, $hashed_password);
        return $stmt->execute();
    }

    /** Returns the user id for a valid API token, or null. */
    function getUserIdByToken($token)
    {
        if (!is_string($token) || $token === '') {
            return null;
        }
        $stmt = $this->connect->prepare("SELECT id FROM users WHERE api_token = ?");
        $stmt->bind_param("s", $token);
        $stmt->execute();
        $row = $stmt->get_result()->fetch_assoc();
        return $row ? (int) $row['id'] : null;
    }

    function userHasAnswers($userId)
    {
        $stmt = $this->connect->prepare("SELECT 1 FROM answers WHERE user_id = ? LIMIT 1");
        $stmt->bind_param("i", $userId);
        $stmt->execute();
        return $stmt->get_result()->num_rows > 0;
    }

    /**
     * Replaces all of a user's answers in a single transaction.
     * $answers is a list of ['questionId' => int, 'optionIndex' => int].
     */
    function saveAnswers($userId, $answers)
    {
        $this->connect->begin_transaction();
        try {
            $stmt = $this->connect->prepare("DELETE FROM answers WHERE user_id = ?");
            $stmt->bind_param("i", $userId);
            $stmt->execute();

            $stmt = $this->connect->prepare(
                "INSERT INTO answers (user_id, question_id, option_index) VALUES (?, ?, ?)");
            foreach ($answers as $answer) {
                $questionId = (int) $answer['questionId'];
                $optionIndex = (int) $answer['optionIndex'];
                $stmt->bind_param("iii", $userId, $questionId, $optionIndex);
                if (!$stmt->execute()) {
                    throw new Exception("Insert failed");
                }
            }
            $this->connect->commit();
            return true;
        } catch (Exception $e) {
            $this->connect->rollback();
            return false;
        }
    }

    /** Returns the answers that feed the footprint calculation. */
    function carbCalc($userId)
    {
        $stmt = $this->connect->prepare(
            "SELECT question_id, option_index FROM answers WHERE user_id = ? AND question_id IN (3, 6, 15)");
        $stmt->bind_param("i", $userId);
        $stmt->execute();
        return $stmt->get_result()->fetch_all(MYSQLI_ASSOC);
    }

    /** Returns every user's answers (option indexes only) for clustering. */
    function clusterCalc()
    {
        $result = mysqli_query($this->connect, "SELECT user_id, question_id, option_index FROM answers");
        return mysqli_fetch_all($result, MYSQLI_ASSOC);
    }

    function getAction()
    {
        $result = mysqli_query($this->connect, "SELECT * FROM actions");
        return mysqli_fetch_all($result, MYSQLI_ASSOC);
    }
}
?>
