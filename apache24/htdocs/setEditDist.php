<?php
$con=mysqli_connect("localhost","root","1234","jungcar");



$drive_idx = $_POST['drive_idx'];




header('content-type: text/html; charset=utf-8');



$query = "select date,distance, memo from drive_history where drive_idx= $drive_idx";



	
if(mysqli_connect_error($con))
echo "Failied to connect : " .mysqli_connect_error();

$result=mysqli_query($con, $query);
$rowCnt= mysqli_num_rows($result);
$arr= array();

for($i=0;$i<$rowCnt;$i++){
      $row= mysqli_fetch_array($result, MYSQLI_ASSOC);
        $arr[$i]= $row;      
}


  $jsonData=json_encode(array("webnautes"=>$arr), JSON_PRETTY_PRINT+JSON_UNESCAPED_UNICODE);
  echo "$jsonData";

mysqli_close($con);
?>