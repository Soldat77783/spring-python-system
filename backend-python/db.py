import mysql.connector

def get_connection():
    return mysql.connector.connect(
        host="localhost",
        port=3306,         
        user="devuser",     
        password="0824532689Era",
        database="client_details"
    )