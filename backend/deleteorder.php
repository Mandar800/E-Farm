<?php
    
    $order_id = $_POST['order_id'];
    
    require 'connect.php';
    
    $res=mysqli_query($conn,"DELETE FROM `orders` WHERE order_id='$order_id'");
    
    $result = array();
 
    if($res){
        if(mysqli_query($conn,"DELETE FROM `orders_details` WHERE order_id='$order_id'")){
             $result['success']="1";
        }
        else{
             $result['success']="0";
        }
    }else{
         $result['success']="0";
    }
   
    echo json_encode($result);
    
    mysqli_close($conn);

 ?>
