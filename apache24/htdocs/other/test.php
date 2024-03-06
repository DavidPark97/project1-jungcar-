<?php
    $con=mysqli_connect("localhost","root","1234","everyhealth");



    if(mysqli_connect_error($con))
        echo "Failed to connect : " .mysqli_connect_error();
        $workout_idx = 1;
    

    $result=mysqli_query($con,"select * from workout where workout_idx = $workout_idx");
    
    $workout_idx = $_POST['workout_idx'];
    
    $rowCnt = mysqli_num_rows($result);
 
    $arr= array();

    for($i=0;$i<$rowCnt;$i++){
           $row= mysqli_fetch_array($result, MYSQLI_ASSOC);
             $arr[$i]= $row;      
     }

    
       $jsonData=json_encode(array("webnautes"=>$arr), JSON_PRETTY_PRINT+JSON_UNESCAPED_UNICODE);
       echo "$jsonData";
  
    mysqli_close($con);

?>