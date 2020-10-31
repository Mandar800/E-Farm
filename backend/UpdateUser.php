<?php
    if ($_SERVER['REQUEST_METHOD']=='POST') {
        $user_id  = $_POST['user_id'];
        $username = $_POST['un'];
        $email = $_POST['email'];
        $pass = $_POST['password'];
        $num = $_POST['no'];
        $add  = $_POST['add'];
        
        require 'connect.php';

        $sql = "UPDATE `users` SET `Name`='$username',`email`='$email',`password`='$pass',`no`='$num',`address`='$add' WHERE id = '$user_id' ";
        $result = mysqli_query($conn, $sql);
        $res = array();
        
        if($result){
            
            $res['success'] = "1";
            $res['message'] = "success";
            
            
        }else{
            $res['success'] = "0";
            $res['message'] = "error";
        }
        
        echo json_encode($res);
    
        mysqli_close($conn);
        
        
    }
?>