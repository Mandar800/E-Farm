<?php

$serverName = "localhost";
$databaseName = "id14954125_db_emandi";
$userName = "id14954125_emandi";
$password= "Mandar@8108219995";
$conn=mysqli_connect($serverName,$userName,$password,$databaseName);
if(!$conn){
  echo "Error Connecting to Database";
}


?>
