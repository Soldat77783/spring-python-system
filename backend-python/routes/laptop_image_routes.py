from fastapi import APIRouter, UploadFile, File, Form
from db import get_connection

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
