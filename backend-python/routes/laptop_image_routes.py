from fastapi import APIRouter, UploadFile, File, Form
from db import get_connection
import base64

router = APIRouter()

@router.post("/upload-image")
async def upload_image(
    user_id: int = Form(...),
    image: UploadFile = File(...)
):
    try:
        image_bytes = await image.read()

        conn = get_connection()
        cursor = conn.cursor()

        cursor.execute("""
            INSERT INTO user_images (user_id, image_name, image_type, image_data)
            VALUES (%s, %s, %s, %s)
        """, (
            user_id,
            image.filename,
            image.content_type,
            image_bytes
        ))

        conn.commit()
        cursor.close()
        conn.close()

        return {"success": True, "message": "Image uploaded successfully"}

    except Exception as e:
        return {"success": False, "error": str(e)}

# -----------------------------
# New endpoint for getting all the user`s images
# -----------------------------
@router.get("/get_user_images")
def get_user_images(user_id: int):
    try:
        conn = get_connection()
        cursor = conn.cursor()

        cursor.callproc("get_user_images", [user_id])
        results = []

        for res in cursor.stored_results():
            rows = res.fetchall()
            for row in rows:
                results.append({"id": row[0], "user_id": row[1], "image_name": row[2], "image_type": row[3], "image_data": base64.b64encode(row[4]).decode("utf-8"),
                                 "uploaded_at": row[5]})
    finally:
        conn.commit()
        conn.close()
        cursor.close()

        return results

    

    
