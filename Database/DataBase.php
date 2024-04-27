<?php
require "DataBaseConfig.php";

class DataBase
{
    public $connect;
    public $data;
    private $sql;
    protected $servername;
    protected $username;
    protected $password;
    protected $databasename;

    public function __construct()
    {
        $this->connect = null;
        $this->data = null;
        $this->sql = null;
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

    function prepareData($data)
    {
        return mysqli_real_escape_string($this->connect, stripslashes(htmlspecialchars($data)));
    }

    function logIn($table, $username, $password) {
        $username = $this->prepareData($username);
        $stmt = $this->connect->prepare("SELECT * FROM $table WHERE username = ?");
        $stmt->bind_param("s", $username);
        $stmt->execute();
        $result = $stmt->get_result();
        $row = $result->fetch_assoc();
    
        if ($row) {
            $dbusername = $row['username'];
            $dbpassword = $row['password'];
            if ($dbusername === $username && password_verify($password, $dbpassword)) {
                $userId = $this->getUserIdByUsername($username);
                $stmt_answers = $this->connect->prepare("SELECT * FROM answers WHERE user_id = ?");
                $stmt_answers->bind_param("s", $userId);
                $stmt_answers->execute();               
                $result_answers = $stmt_answers->get_result();
                if ($result_answers->num_rows > 0) {
                    return "Login Success";
                } else {
                    return "Survey";
                }
            } else {
                return "Username or Password wrong";
            }
        } else {
            return "Username or Password wrong";
        }
    }

    function signUp($table, $email, $username, $password) {
        $stmt = $this->connect->prepare("INSERT INTO $table (email, username, password) VALUES (?, ?, ?)");
        $hashed_password = password_hash($password, PASSWORD_DEFAULT);
        $stmt->bind_param("sss", $email, $username, $hashed_password);
        return $stmt->execute();
    }

    function carbCalc($table, $userId) {
        $userId = $this->prepareData($userId);
        $table = mysqli_real_escape_string($this->connect, $table);
    
        $stmt = mysqli_prepare($this->connect, "SELECT * FROM $table WHERE user_id = ? AND question_id IN (?, ?, ?)");
    
        mysqli_stmt_bind_param($stmt, 'iiii', $userId, $q1, $q2, $q3);
    
        $q1 = 3;
        $q2 = 6;
        $q3 = 15;
    
        mysqli_stmt_execute($stmt);
    
        $result = mysqli_stmt_get_result($stmt);
    
        $rows = mysqli_fetch_all($result, MYSQLI_ASSOC);
    
        // Close the statement
        mysqli_stmt_close($stmt);
    
        return $rows;
    }
    

    function clusterCalc($table) {
        $this->sql = "SELECT * FROM " . $table;
        $result = mysqli_query($this->connect, $this->sql);
        
        $rows = array();
        while($row = mysqli_fetch_assoc($result)) {
            $rows[] = $row;
        }
        return $rows;
    }

    function insertAns($table, $userId, $questionId, $optionIndex) {
            $stmt = $this->connect->prepare("INSERT INTO $table (user_id, question_id, option_index) VALUES (?, ?, ?)");
            $stmt->bind_param("iii", $userId, $questionId, $optionIndex);
            return $stmt->execute();
    }

    function getUserIdByUsername($username) {
        $stmt = $this->connect->prepare("SELECT id FROM users WHERE username = ?");
        $stmt->bind_param("s", $username);
        $stmt->execute();
        $result = $stmt->get_result();
        if ($result->num_rows > 0) {
            $row = $result->fetch_assoc();
            return $row['id'];
        } else {
            return null; 
        }
    }

    function getAction() {
        $stmt = $this->connect->prepare("SELECT * FROM actions");
        $stmt->execute();
        $result = $stmt->get_result();
        $rows = array();
        while($row = mysqli_fetch_assoc($result)) {
            $rows[] = $row;
        }
        return $rows;
    }
    
}?>