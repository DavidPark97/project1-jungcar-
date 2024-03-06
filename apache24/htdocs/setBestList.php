<?php
$con=mysqli_connect("localhost","root","1234","jungcar");

$type = $_POST['type'];




header('content-type: text/html; charset=utf-8');



$query = "select board_idx, subject,date, 
(select count(*) from shboard where board_idx = b.board_idx)+(select count(*) from reco where board_idx = b.board_idx) as count,
 row_number() over (order by (select count(*) from shboard where board_idx = b.board_idx)+(select count(*) from reco where board_idx = b.board_idx) desc) as ranking
 , name from board b, board_type where b.board_type = board_type.board_type and b.board_type not in (1,11) and ";

 if(strcmp($type,"week")==0){
 $query = $query."date between DATE_ADD(now(),interval -1 week) and now() limit 10"; 
}else if(strcmp($type,"month")==0){
  $query = $query."month(date) = month(now()) limit 10"; 
}else{
  $query = $query."year(date) = year(now()) limit 10"; 
}

	
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