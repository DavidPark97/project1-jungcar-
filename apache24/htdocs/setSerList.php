<?php
$con=mysqli_connect("localhost","root","1234","jungcar");



$car_idx = $_POST['car_idx'];

$today = "2023-07-01";



header('content-type: text/html; charset=utf-8');



$query = "select type,stdkm,stdmth,img,maintain.main_type, type_name,IFNULL(round((select sum(distance) from drive_history 
where drive_history.car_idx = maintain_history.car_idx and 
drive_history.car_idx = $car_idx and drive_history.date >= max(maintain_history.date))/stdkm,2),
round((select ifnull(sum(distance),0) from drive_history where car_idx = $car_idx)/stdkm,2)) as dist, 
round(if(stdmth=0,0,IFNULL(timestampdiff(month,max(maintain_history.date),'$today'),period_diff(concat(year('$today'),date_format('$today','%m')),
(select regist_de from refer,car where plnumber = refer.vhrno and car_idx = $car_idx ))
)/stdmth),2) as diff from drive_history right outer join maintain_history on maintain_history.car_idx = drive_history.car_idx
 right outer join maintain on maintain_history.main_type = maintain.main_type and maintain_history.car_idx=$car_idx group by maintain.main_type 
order by  (case when dist>diff then dist else diff end) desc";


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