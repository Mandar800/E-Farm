<?php
    
    $order_id = $_POST['order_id'];
    
    require 'connect.php';
    
    $res=mysqli_query($conn,"UPDATE `orders` SET `status`=1 WHERE order_id='$order_id'");
    
    $result = array();
 
    if($res){
        $result['success']="1";
    }else{
         $result['success']="0";
    }
   
    echo json_encode($result);
    
    mysqli_close($conn);

 ?>
