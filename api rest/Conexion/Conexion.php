<?php

class Conexion {
    protected static $db;
    private $driver = "mysql";
    private $host = "localhost";
    private $database = "bdprueba";
    private $user = "root";
    private $password = "root";
    private $port = "3306";

    //private construct - class cannot be instatiated externally.
    private function __construct() {

        try {
            self::$db = new PDO($this->driver . ":host=" . $this->host . ";port=" . $this->port . ";dbname=" . $this->database, $this->user, $this->password);
            self::$db->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
        } catch (PDOException $e) {
            echo "Connection Error: " . $e->getMessage();
        }
    }
    // get connection function. Static method - accessible without instantiation
    public static function getConnection() {
        //Guarantees single instance, if no connection object exists then create one.
        if (!self::$db) {
            //new connection object.
            new Conexion();
        }

        //return connection.
        return self::$db;
    }

    private function __clone() {
        
    }

}
