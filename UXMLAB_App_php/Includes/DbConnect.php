<?php

class DbConnect {
  private $con;

  function __construct() {}
  
  function connect() {
    //Including the constants.php file to get the database constants
    include_once dirname(__FILE__) . '/Constants.php';
 
    //connecting to mysql database
    $this->con = new mysqli(DB_HOST, DB_USERNAME, DB_PASSWORD, DB_NAME);

    //Checking if any error occured while connecting
    if (mysqli_connect_errno()) {
        echo "Failed to connect to MySQL: " . mysqli_connect_error();
        return null;
    }

    //finally returning the connection link
    return $this->con;
  }
}
?>
