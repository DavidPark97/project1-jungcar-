<?php
$con=mysqli_connect("localhost","root","1234","everyhealth");


$workout_idx =$_POST['workout_idx'];
$orders = $_POST['orders'];
$orders=$orders+1;
	

if(mysqli_connect_error($con))
echo "Failied to connect : " .mysqli_connect_error();
$res = mysqli_fetch_array(mysqli_query($con,"select count(*) as cnt from orders where workout_idx = $workout_idx"),MYSQLI_ASSOC);
$result=mysqli_fetch_array(mysqli_query($con, "select w.workout_name, o.detail, order_img from orders as o, workout as w where w.workout_idx=o.workout_idx and o.workout_idx = $workout_idx and work_orders = $orders"),MYSQLI_ASSOC);

$arr= array("cnt"=>$res["cnt"], "detail"=>$result["detail"], "order_img"=>$result["order_img"],"name"=>$result['workout_name']);
$test =array($arr);


  $jsonData=json_encode(array("webnautes"=>$test), JSON_PRETTY_PRINT+JSON_UNESCAPED_UNICODE);
  echo "$jsonData";

mysqli_close($con);
?>