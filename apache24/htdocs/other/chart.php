<?php
    $con=mysqli_connect("localhost","root","1234","everyhealth");
   
    $user_idx =$_POST['user_idx'];


    $query1 = "SELECT preference, inbody, gender FROM user WHERE user_idx =".$user_idx.";";
    $result = mysqli_fetch_array(mysqli_query($con, $query1));
    
    $prefer = $result["preference"];

    if($prefer==1 || $prefer==2 ){
        $arr2=array();
        $i=0;
        for($month=7;$month<=12;$month++){
        $query1 = "SELECT sum(count*dec_kcal) as total FROM (SELECT count, workout_idx FROM daily_data WHERE user_idx =".$user_idx." and month(date) ='".$month."') as a  inner join workout where a.workout_idx = workout.workout_idx;";
        $query2 = "SELECT round(sum(gram*kcal)) as total FROM (SELECT gram, food_idx FROM daily_eat WHERE user_idx = ".$user_idx." and month(date) ='".$month."') as a inner join food where a.food_idx = food.food_idx;";
       
        $total_work = mysqli_fetch_array(mysqli_query($con, $query1), MYSQLI_ASSOC);
        $total_eat = mysqli_fetch_array(mysqli_query($con, $query2), MYSQLI_ASSOC);
        
        $arr2[$i]=array("total" => $total_work["total"]-$total_eat["total"],"month"=>$month);
        $i++;
        }
    }else{
        $arr2=array();
        $i=0;
        for($month=7;$month<=12;$month++){
        $query1 = "select count(distinct(date)) as cnt from daily_data where month(date)='$month' and user_idx = $user_idx";
        $result = mysqli_fetch_array(mysqli_query($con,$query1),MYSQLI_ASSOC);
        $arr2[$i]=array("total" => $result["cnt"],"month"=>$month);
        $i++;
        }
    }
    $jsonData=json_encode(array("webnautes"=>$arr2), JSON_PRETTY_PRINT+JSON_UNESCAPED_UNICODE);
   
    echo "$jsonData";
    // $user = "$_POST['user']";
    // $date = "'".$_POST['date']."'";


    mysqli_close($con);
?>