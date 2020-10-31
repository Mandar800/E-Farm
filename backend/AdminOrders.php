<?php
    
    
    require 'connect.php';
    $sql = "SELECT user_id,orders.order_id,Name, no, address status, timestamp, item_id, title, cost, qty FROM `orders_details` INNER join `orders` on orders_details.order_id = orders.order_id INNER join `users` on user_id=users.id order by timestamp DESC,order_id DESC";
    
    $res=mysqli_query($conn,"SELECT * FROM `orders` order by timestamp DESC");
    
    $result = array();
 
    while($row = mysqli_fetch_array($res)){
        $user_id = $row['user_id'];
        $order_id = $row['order_id'];
        $status = $row['status'];
        $timestamp = $row['timestamp'];
        $user = mysqli_query($conn,"SELECT * FROM `users` where id='$user_id'");
        $row2 = mysqli_fetch_array($user);
        $Name = $row2['Name'];
        $no = $row2['no'];
        $add = $row2['address'];
        $order =  mysqli_query($conn,"SELECT * FROM `orders_details` where order_id='$order_id'");
        $order_data = array();
        while($row3 = mysqli_fetch_array($order)){
            array_push($order_data,array($row3['item_id'],$row3['title'],$row3['cost'],$row3['qty']));
        }
        array_push($result,array($user_id,$order_id,$status,$timestamp,$Name,$no,$add,$order_data));
        
    }
    
    echo json_encode($result,JSON_UNESCAPED_SLASHES | JSON_UNESCAPED_UNICODE);
    
    mysqli_close($conn);

 ?>
