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

#get the image description that belongs to the specific user
@router.get("/get_image_description")
async def set_Image_Description(id: int):
    conn = get_connection()
    cursor = conn.cursor()

    try:
         cursor.callproc("set_user_images_with_description", [id])
         conn.commit()

         result = []

         for res in cursor.stored_results():
            result = res.fetchall()

    finally:
        conn.close()
        cursor.close()

        return result

    



    