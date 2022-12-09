import boto3

def s3_connection(data):
    s3 = None
    try:
        s3 = boto3.resource(
            service_name='s3',
            region_name=data['BUCKET_REGION'],
            aws_access_key_id=data['ACCESS_KEY_ID'],
            aws_secret_access_key=data['SECRET_KEY']
        )
        bucket = data['BUCKET_NAME']
    except Exception as e:
        print(e)
        exit(1)
    else:
        print("s3 bucket connected!")
        return s3, bucket
