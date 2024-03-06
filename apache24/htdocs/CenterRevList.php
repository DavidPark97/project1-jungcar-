<?php
$con=mysqli_connect("localhost","root","1234","jungcar");



$center_idx = $_POST['center_idx'];




header('content-type: text/html; charset=utf-8');



$query ="select comment, star, c.date, 
(select count(distinct(date)) from maintain_history x where x.car_idx = m.car_idx and x.center_idx = m.center_idx and center_idx = $center_idx) as visit 
from center_review c, maintain_history m where c.center_idx = m.center_idx and c.car_idx = m.car_idx and c.center_idx = $center_idx group by review_idx";


	
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