<?php
$con=mysqli_connect("localhost","root","1234","jungcar");

$name = $_POST['name'];
$latitude = $_POST['latitude'];
$longitude = $_POST['longitude'];





header('content-type: text/html; charset=utf-8');



$query = "select name from center where name = '$name' and round(latitude,4) =  round($latitude,4) and round(longitude,3) = round($longitude,3)";


	
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