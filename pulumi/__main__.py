import pulumi_aws as aws

bucket = aws.s3.Bucket(resource_name="pulumi-demo", bucket="pulumi-demo", tags={
    "Owner": "pulumi-demo",
    "Purpose": "demo",
    "Place": "atlas"
})
