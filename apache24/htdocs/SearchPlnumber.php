<?php
$con=mysqli_connect("localhost","root","1234","jungcar");


//파일 img라는 파일을 받아온다 (안드로이드로 부터)
$plnumber = $_POST['plnumber'];
//파이썬 실행

$query ="select cnm as model, brand, vhrno as plnumber, concat(use_fuel_nm,' ',dspval,'cc') as fuel from refer, model where vhrno = '$plnumber' and model.year = refer.cnm;";
	
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