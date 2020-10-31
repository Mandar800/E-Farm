<?php
    
    $id = $_POST['id'];
    $title = $_POST['title'];
    $cost = $_POST['cost'];
    $imgstr = $_POST['image'];
    
    require 'connect.php';
    
    if($id == "-1"){
        
        $filename = $title.".jpg";-
        
        file_put_contents("images/".$filename,base64_decode($imgstr));
        
        $res=mysqli_query($conn,"INSERT INTO `items`( `title`, `cost`, `img`) VALUES ('$title','$cost','$filename')");
        
        $result = array();
        
        if($res){
            $result['success']="1";
        }else{
             $result['success']="0";
        }
            
    }else{
        $resu=mysqli_query($conn,"SELECT `img` FROM `items` WHERE itemId='$id'");
        $row = mysqli_fetch_array($resu);
        unlink("images/".$row['img']);
        
        $filename = $title.".jpg";-
        file_put_contents("images/".$filename,base64_decode($imgstr));
        
        $res=mysqli_query($conn,"UPDATE `items` SET `title`='$title',`cost`='$cost',`img`='$filename' WHERE itemId = '$id'");
        
        $result = array();
     
        if($res){
            $result['success']="1";
        }else{
             $result['success']="0";
        }
            
    }
    
    
   
    echo json_encode($result);
    
    mysqli_close($conn);

 ?>
