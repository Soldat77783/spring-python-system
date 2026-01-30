from fastapi import APIRouter, Form
from db import get_connection

router = APIRouter()

@router.post("/upload_image_description")
async def set_Image_Description(id: int = Form(...), description: str = Form(...)):
    try:
        conn = get_connection()
        cursor = conn.cursor()

        cursor.callproc("set_user_images_with_description", [id, description])
        conn.commit()

    finally:
        conn.close()
        cursor.close()

        return {f"Description {id} {description} description uploaded successfully!"}

@router.get("/Get_Image_Description")
async def get_image_description(id: int):
    conn = get_connection()
    cursor = conn.cursor()

    try:
        cursor.callproc("Get_Image_Description", [id])

        rows = []
        for res in cursor.stored_results():
            rows = res.fetchall()

        #convert rows → JSON objects
        results = []
        for row in rows:
            results.append({
                "id": row[0],
                "description": row[1],
                "updated_at": row[2],
                "created_at": row[3],
                "image_id": row[4],
                "user_id": row[5],
                "image_name": row[6],
                "image_type": row[7],
                "uploaded_at": row[8]
            })

        return results

    finally:
        cursor.close()
        conn.close()

    



    