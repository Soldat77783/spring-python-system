from fastapi import FastAPI, Form
from routes.laptop_image_routes import router as image_router
import mysql.connector

app = FastAPI()

app.include_router(image_router)

def get_connection():
    return mysql.connector.connect(
        host="localhost",
        port=3306,         
        user="devuser",     
        password="0824532689Era",
        database="client_details"
    )

# -----------------------------
# New endpoint for getting all the uerss and their data
# -----------------------------
@app.get("/users")
def get_users():
    conn = get_connection()
    cursor = conn.cursor(dictionary=True)

    cursor.execute("""
        SELECT id, username, usersurname
        FROM users
    """)
#you can also do the following below:
#cursor.fetchone()   # one row
#cursor.fetchmany(5) # first 5 rows
#but for now i am getting all of the SQL results back in fetchall()
    result = cursor.fetchall()

    cursor.close()
    conn.close()

    return result

# -----------------------------
# New endpoint for getting all the uerss and their data for the unique user`s dashboard including passwords
# -----------------------------
@app.get("/allUsers")
def get_all_users():
    conn = get_connection()
    cursor = conn.cursor(dictionary = True)
    try:
        cursor.callproc("get_all_users")  #SP called here
        result = []
        for res in cursor.stored_results():
            result = res.fetchall()
    finally:
        cursor.close()
        conn.close()

        return result

# -----------------------------
# New endpoint for registration
# -----------------------------
@app.post("/register")
def register_user(username: str = Form(...), usersurname: str = Form(...)):
    conn = get_connection()
    cursor = conn.cursor()
    cursor.execute(
        "INSERT INTO users (username, usersurname, password) VALUES (%s, %s, %s)",
        (username, usersurname, "defaultpassword")  # we only insert username and usersurname
    )
    conn.commit()
    cursor.close()
    conn.close()
    
    return {"message": f"User {username} {usersurname} registered successfully!"}

# ------------------------------------
# New endpoint for logging a user in
# ------------------------------------
#remember with Form(...) it MUST always use POST and not get because you are entereing data in the UI and posting it TO GET data in turn but thats dealt with in the body of the code
@app.post("/login")
def login_user(password: str = Form(...), username: str = Form(...)):
    conn = get_connection()
    cursor = conn.cursor(dictionary = True) #dictionary = True displays data nicely instead of all inline
    cursor.execute("SELECT id, username, usersurname FROM users WHERE username = %s AND password = %s", (username, password))
    result = cursor.fetchone()
    cursor.close()
    conn.close()

    if result is None:
        return {"success": False, "message": "Invalid user credentials"}
    
    return{"success": True, "result": result}
