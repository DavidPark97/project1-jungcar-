<?php
$con=mysqli_connect("localhost","root","1234","jungcar");



$latitude =  $_POST['latitude'];
$longitude =  $_POST['longitude'];
$car_idx = $_POST['car_idx'];




header('content-type: text/html; charset=utf-8');



$query ="SELECT round((6371 * acos ( cos ( radians($latitude) )
* cos( radians( latitude ) )
          * cos( radians( longitude) - radians($longitude) )
          + sin ( radians($latitude) ) * sin( radians( latitude ) )
       )
   ),2) AS distance,name,address,center.center_idx as center_idx, count(center_review.center_idx) as cnt, latitude, longitude,  round(ifNULL(avg(star),0),1) as rate,tel,
   ifnull((select count(*) from maintain_history where center.center_idx = maintain_history.center_idx and car_idx = $car_idx group by maintain_history.center_idx),0) as visit 
FROM center left outer join center_review on center.center_idx = center_review.center_idx
where longitude between $longitude - 0.05 and $longitude + 0.05 and latitude between $latitude - 0.05 and $latitude + 0.05
group by center.center_idx order by distance asc limit 100";


	
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