<?php
    
    $id = $_POST['id'];
    
    require 'connect.php';
    
        $resu=mysqli_query($conn,"SELECT `img` FROM `items` WHERE itemId='$id'");
        $row = mysqli_fetch_array($resu);
        unlink("images/".$row['img']);
        
        $res=mysqli_query($conn,"DELETE FROM `items` WHERE itemId = '$id'");
        
        $result = array();
     
        if($res){
            $result['success']="1";
        }else{
             $result['success']="0";
        }
            
   
    echo json_encode($result);
    
    mysqli_close($conn);

 ?>
