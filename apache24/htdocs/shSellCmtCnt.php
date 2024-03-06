<?php
$con=mysqli_connect("localhost","root","1234","jungcar");
header('content-type: text/html; charset=utf-8');


$sell_idx =  $_POST['sell_idx'];




$query = "select count(*) as cnt from sell_comment where sell_idx=$sell_idx";


	
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