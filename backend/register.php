<?php

if ($_SERVER['REQUEST_METHOD']=='POST') {
    
    $serverName = "localhost";
    $databaseName = "id14954125_db_emandi";
    $userName = "id14954125_emandi";
    $password= "Mandar@8108219995";
    $conn=mysqli_connect($serverName,$userName,$password,$databaseName);
    if(!$conn){
      echo "Error Connecting to Database";
    }
    
    $email = $_POST['email'];
    $pass = $_POST['password'];
    $username = $_POST['un'];
    $num = $_POST['no'];
    $add = $_POST['add'];
    
    

    $sql = "SELECT * FROM users WHERE email='$email' ";

    $response = mysqli_query($conn, $sql);
    
    $result = array();
   
    
    if ( mysqli_num_rows($response) > 0 ) {
        
        $result['success'] = "0";
        $result['message'] = "duplicate email";
        echo json_encode($result);
        mysqli_close($conn);
        
        
    }
    else{
        
        $sql = "INSERT INTO `users`( `Name`, `email`, `password`, `no`, `address`) VALUES ('$username','$email','$pass','$num','$add')";
        
        if(mysqli_query($conn, $sql)){
            $last_id = mysqli_insert_id($conn);
            $result['success'] = "1";
            $result['message'] = "success";
            $result['id']=$last_id;
            echo json_encode($result);
            mysqli_close($conn);

        }else{
            $result['success'] = "0";
            $result['message'] = "error inserting";
            echo json_encode($result);
            mysqli_close($conn);
        }
        
    }
    
}

?>