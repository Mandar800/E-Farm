<?php
    
    
    require_once 'connect.php';

    $res=mysqli_query($conn,"Select * from items");
    $result = array();
    
    while($row = mysqli_fetch_array($res)){
        $imgstr = file_get_contents("images/".$row[3]);
    array_push($result,array('item_id'=>$row[0],
        'title'=>$row[1],
    'cost'=>$row[2],
    'img'=>base64_encode($imgstr) 
    ));
    }
    
    echo json_encode(array("result"=>$result),JSON_UNESCAPED_SLASHES | JSON_UNESCAPED_UNICODE);
    
    mysqli_close($conn);

 ?>
