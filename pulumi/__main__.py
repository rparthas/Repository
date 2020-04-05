import pulumi_aws as aws
import pulumi
import os
import mimetypes

config = pulumi.Config()
site_dir = config.get("site")

bucket = aws.s3.Bucket(resource_name="pulumi-demo",
                       bucket="pulumi-demo",
                       tags={
                           "Owner": "pulumi-demo",
                           "Purpose": "demo",
                           "Place": "atlas"
                       },
                       website={"indexDocument": "index.html"})
filepath = os.path.join(site_dir, "index.html")
mimetype,_=mimetypes.guess_type(filepath)
bucket_object = aws.s3.BucketObject(
    "index.html",
    bucket=bucket.bucket,
    acl="public-read",
    source=filepath,
    content_type=mimetype)

pulumi.export('bucket_name', bucket.bucket)
pulumi.export('bucket_endpoint', bucket.website_endpoint)
