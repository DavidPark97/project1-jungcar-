<?php
$con=mysqli_connect("localhost","root","1234","jungcar");



$sell_idx = $_POST['sell_idx'];




header('content-type: text/html; charset=utf-8');



$query = "select option_name as name from optionn left outer join sell_option on sell_option.option_idx = optionn.option_idx where 
(option_name in('파워전동 트렁크', '열선핸들', '타이어 공기압 센서', '후측방 경보시스템', '열선시트', '통풍시트', '어라운드뷰', '메모리시트', '전자식주차브레이크')
 or option_name like '크루즈컨트롤%' ) and sell_idx = $sell_idx";



	
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