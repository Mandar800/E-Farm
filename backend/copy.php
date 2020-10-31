<?php
    if ($_SERVER['REQUEST_METHOD']=='POST') {
        
        $user_id = $_POST['user_id'];
        
        require 'connect.php';
        
        $res=mysqli_query($conn,"SELECT * FROM orders WHERE user_id='$user_id'");
        $result=array();
        
        
        if($res){
           while($row = mysqli_fetch_array($res)){
                
                $order_id = $row['order_id'];
                $status = $row['status'];
                $order = mysqli_query($conn,"SELECT * FROM `orders_details` WHERE order_id='$order_id'");
                
                if($order){
                    $items = array();
                    while($row2 = mysqli_fetch_array($order)){
                        array_push($items,array(
                             $row2['item_id'],
                            $row2['qty'],
                            ));
                    }
                    
                    array_push($result,array($order_id => $items));
                    
                    
                }
        
            }
   
    echo json_encode($result );
    
    mysqli_close($conn);

        }
        
    }
?>