<?php
$con=mysqli_connect("localhost","root","1234","everyhealth");


$user_idx = $_POST['user_idx'];
$food_idx = $_POST['food_idx'];
$date = $_POST['date'];


	

if(mysqli_connect_error($con))
echo "Failied to connect : " .mysqli_connect_error();

$result=mysqli_query($con, "select f.food_name, d.gram from food as f, daily_eat as d where d.date = '$date' and d.user_idx=$user_idx and d.food_idx = f.food_idx and d.food_idx=$food_idx");
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