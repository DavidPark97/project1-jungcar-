<?php
$con=mysqli_connect("localhost","root","1234","jungcar");



$car_idx = $_POST['car_idx'];
$year = $_POST['year'];




header('content-type: text/html; charset=utf-8');



$query = "select month(m.date) as month, 
ifnull((select round(sum(charge)/1000,0) 
from maintain_history where year(date) = $year and month(date) = month(m.date) and car_idx = $car_idx group by month(m.date)),0) as maintain, 
ifnull((select round(sum(charge)/1000,0) from oil_history where year(date) = $year and month(date) = 
month(m.date) and car_idx = $car_idx group by month(m.date)),0) as oil
 from maintain_history m where year(m.date) = $year and m.car_idx = $car_idx group by month(m.date) 
union
select month(o.date) as month, 
ifnull((select round(sum(charge)/1000,0) 
from maintain_history where year(date) = $year and month(date) = month(o.date) and car_idx = $car_idx group by month(o.date)),0) as maintain, 
ifnull((select round(sum(charge)/1000,0) from oil_history where year(date) = $year and
 month(date) = month(o.date) and car_idx = $car_idx group by month(o.date)),0) as oil 
from oil_history o where year(o.date) = $year and o.car_idx = $car_idx group by month(o.date) order by month;";


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