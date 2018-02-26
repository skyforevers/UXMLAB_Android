<?php

$id = 12341234;
$password = "12341234";

class DbOperation {
  private $con;

  function __construct() {
    require_once dirname(__FILE__) . '/DbConnect.php';
    $db = new DbConnect();
    $this->con = $db->connect();
  }

  //Method to create a new user
  function registerUser($id, $pass, $name, $email, $is_student)
  {
      if (!$this->isUserExist($email)) {
          //$password = md5($pass);
          $password = $pass; // 추후 암호화 방식 추가 필요
          $stmt = $this->con->prepare("INSERT INTO users (id, password, name, email, is_student) VALUES (?, ?, ?, ?, ?)");
          $stmt->bind_param("isssi", $id, $pass, $name, $email, $is_student);
          if ($stmt->execute())
              return USER_CREATED;
          return USER_CREATION_FAILED;
      }
      return USER_EXIST;
  }

  //Method for user login
  function userLogin($id, $pass)
  {
      //$password = md5($pass); // 추후 다른 암호화 방식으로 교체 필요
      $password = $pass;
      $stmt = $this->con->prepare("SELECT id FROM users WHERE id = ? AND password = ?");
      $stmt->bind_param("is", $id, $password);
      $stmt->execute();
      $stmt->store_result();
      return $stmt->num_rows > 0;
  }

  // //Method to update profile of user
  // function updateProfile($id, $name, $email, $pass, $gender)
  // {
  //     $password = md5($pass);
  //     $stmt = $this->con->prepare("UPDATE users SET name = ?, email = ?, password = ?, gender = ? WHERE id = ?");
  //     $stmt->bind_param("ssssi", $name, $email, $password, $gender, $id);
  //     if ($stmt->execute())
  //         return true;
  //     return false;
  // }

  //Method to get user by email
  function getUserById($id)
  {
      $stmt = $this->con->prepare("SELECT id, name, email, is_student FROM users WHERE id = ?");
      $stmt->bind_param("i", $id);
      $stmt->execute();
      $stmt->bind_result($id, $name, $email, $is_student);
      $stmt->fetch();
      $user = array();
      $user['id'] = $id;
      $user['name'] = $name;
      $user['email'] = $email;
      $user['is_student'] = $is_student;
      return $user;
  }

  // //Method to get all users
  // function getAllUsers(){
  //     $stmt = $this->con->prepare("SELECT id, name, email, gender FROM users");
  //     $stmt->execute();
  //     $stmt->bind_result($id, $name, $email, $gender);
  //     $users = array();
  //     while($stmt->fetch()){
  //         $temp = array();
  //         $temp['id'] = $id;
  //         $temp['name'] = $name;
  //         $temp['email'] = $email;
  //         $temp['gender'] = $gender;
  //         array_push($users, $temp);
  //     }
  //     return $users;
  // }

  //Method to check if email already exist
    function isUserExist($email)
    {
        $stmt = $this->con->prepare("SELECT id FROM users WHERE email = ?");
        $stmt->bind_param("s", $email);
        $stmt->execute();
        $stmt->store_result();
        return $stmt->num_rows > 0;
    }
}

// /* userLogin Test */
// $db = new DbOperation();
// $result = $db->userLogin($id, $password);
// echo $result;

?>
