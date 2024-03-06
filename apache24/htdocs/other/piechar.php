
<?php
$con=mysqli_connect("localhost","root","1234","everyhealth");



$user_idx = $_POST['user_idx'];


if(mysqli_connect_error($con))
echo "Failied to connect : " .mysqli_connect_error();

$result=mysqli_query($con,"select count(daily_data.workout_idx) as ranker,workout_name from daily_data,workout where user_idx = $user_idx and daily_data.workout_idx = workout.workout_idx group by daily_data.workout_idx order by ranker desc;;");
$rowCnt= mysqli_num_rows($result);
$arr= array();

for($i=0;$i<6;$i++){
      $row= mysqli_fetch_array($result, MYSQLI_ASSOC);
        $arr[$i]= $row;      
}


  $jsonData=json_encode(array("webnautes"=>$arr), JSON_PRETTY_PRINT+JSON_UNESCAPED_UNICODE);
  echo "$jsonData";

mysqli_close($con);
?>