<?php

if ($_SERVER['REQUEST_METHOD']=='POST') {

    $user_id = $_POST['user_id'];
    $cartlist = $_POST['data'];
    $json = json_decode($cartlist, true);
    
    
    require 'connect.php';

    $sql = "INSERT INTO `orders`(`user_id`) VALUES ('$user_id');";

    $response = mysqli_query($conn, $sql);
    
    $result = array();
    $result['success'] = "0";
    
    if ( $response) {
        $last_id = mysqli_insert_id($conn);
        
            foreach ($json as $key => $value) {
                
                $res = mysqli_query($conn,"SELECT * FROM `items` WHERE itemId='$key'" );
                $row = mysqli_fetch_row($res);
                $title = $row[1];
                $title = mysqli_real_escape_string($conn, $title);
                $cost = $row[2];
                $sql = "INSERT INTO `orders_details`(`order_id`, `item_id`, `title`, `cost`, `qty`) VALUES ('$last_id','$key','$title','$cost' ,'$value')";
                $qres = mysqli_query($conn, $sql) or die(mysqli_error($conn));
                if($qres){
                    array_push($result,1);
                }else{
                    echo("Error description: " . $qres ->error);
                }
                    
                    
                
            }
            $result['success'] = "1";
            echo json_encode($result);
            mysqli_close($conn);
            
        
    }else{
        $result['success'] = "0";
        echo json_encode($result,JSON_UNESCAPED_SLASHES | JSON_UNESCAPED_UNICODE);
        mysqli_close($conn);
        
    }

}

?>