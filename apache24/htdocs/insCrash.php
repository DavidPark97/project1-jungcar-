<?php
$con=mysqli_connect("localhost","root","1234","jungcar");



$car_idx = $_POST['car_idx'];
$date = $_POST['date'];
$place = $_POST['place'];
$memo = $_POST['memo'];





header('content-type: text/html; charset=utf-8');
$result=mysqli_query($con, "select max(crash_idx)+1 as crash_idx from crash_history;");
$crash = mysqli_fetch_array($result);
$crash_idx = $crash['crash_idx'];

if(is_null($crash_idx)){
    $crash_idx="1";
}


$query2 = "insert into crash_history (crash_idx,car_idx,date,memo,location) values ($crash_idx,$car_idx,date('$date'),'$memo','$place')";



	
if(mysqli_connect_error($con))
echo "Failied to connect : " .mysqli_connect_error();

$result2=mysqli_query($con, $query2);


$arr=array("crash_idx" => $crash_idx);
$arr2=array($arr);
$jsonData=json_encode(array("webnautes"=>$arr2), JSON_PRETTY_PRINT+JSON_UNESCAPED_UNICODE);

echo $jsonData;

mysqli_close($con);
?>