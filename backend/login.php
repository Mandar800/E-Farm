<?php

if ($_SERVER['REQUEST_METHOD']=='POST') {

    $email = $_POST['email'];
    $pass = $_POST['password'];
    
    require_once 'connect.php';

    $sql = "SELECT * FROM users WHERE email='$email' ";

    $response = mysqli_query($conn, $sql);
    
    $result = array();
    $result['login'] = array();
    
    if ( mysqli_num_rows($response) === 1 ) {
        
        $row = mysqli_fetch_assoc($response);
        
        if ( $pass== $row['password'] ) {
           
            $index['Name'] = $row['Name'];
            $index['email'] = $row['email'];
            $index['id'] = $row['id'];
            $index['pass'] = $row['password'];
            $index['no'] = $row['no'];
            $index['add'] = $row['address'];

            array_push($result['login'], $index);

            $result['success'] = "1";
            $result['message'] = "success";
            echo json_encode($result);
            
            mysqli_close($conn);

        } else {

            $result['success'] = "0";
            $result['message'] = "Worng Password";
            echo json_encode($result);

            mysqli_close($conn);

        }

    }else{
        $result['success'] = "0";
        $result['message'] = "error";
        echo json_encode($result);
        mysqli_close($conn);
        
    }

}

?>