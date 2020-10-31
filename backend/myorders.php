<?php
    if ($_SERVER['REQUEST_METHOD']=='POST') {
        
        $user_id = $_POST['user_id'];
        
        require 'connect.php';
        
        $res=mysqli_query($conn,"SELECT * FROM orders WHERE user_id='$user_id' order by timestamp DESC");
        $result=array();
        
        if($res){
           while($row = mysqli_fetch_array($res)){
                
                $order_id = $row['order_id'];
                $date = $row['timestamp'];
                $status = $row['status'];
                $order = mysqli_query($conn,"SELECT item_id, title, cost, qty, order_id FROM orders_details where order_id='$order_id'")or die(mysqli_error($conn));
                
                if($order){
                    $items = array();
                    while($row2 = mysqli_fetch_array($order)){
                        array_push($items,array(
                             
                             $row2['title'],
                             $row2['cost'],
                            $row2['qty'],
                            ));
                    }
                    
                    array_push($result,array('status'=>$status,'date'=>$date,'items' => $items));
                    
                    
                }
        
            }
   
    echo json_encode($result );
    
    mysqli_close($conn);

        }
        
    }
?>