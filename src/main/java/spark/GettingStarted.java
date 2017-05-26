package spark;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.execution.vectorized.ColumnarBatch.Row;
import org.bson.Document;

import com.mongodb.spark.MongoSpark;
import com.mongodb.spark.rdd.api.java.JavaMongoRDD;
import com.mongodb.spark.sql.*;
import static java.util.Collections.singletonList;
import static spark.Spark.*;

import java.util.HashMap;
import java.util.Map;

import com.mongodb.spark.config.WriteConfig;

public final class GettingStarted {

  public static void main(final String[] args) throws InterruptedException {
	   TraitementSpark t=new TraitementSpark();
	  get("/users/:id", (req, res) -> t.traitement(req.params(":id")));
	  System.out.println("ok");

}
  
 
}