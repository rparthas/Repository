import os
import glob

import yaml

root = "/Users/xxx/"
folder_path = '%sMy Drive/Obsidian/a/b/c' % root

for file_name in glob.glob(os.path.join(folder_path, '*')):
    with open(file_name, 'r') as file:
        content = file.read()
        start = content.find('---')
        end = content.find('---', start + 3)
        if start != -1 and end != -1:
            front_matter = content[start + 3:end - 3]
    try:
        front_matter_yaml = yaml.safe_load(front_matter)
        front_matter_yaml.pop('author', None)
        front_matter_yaml.pop('type', None)
        source = front_matter_yaml.pop('Source', None)
        with open(root + "Literature/" + os.path.basename(file_name), "w") as new_file:
            new_file.write("---\n")
            yaml.dump(front_matter_yaml, new_file)
            new_file.write("---\n\n")
            if source is not None:
                new_file.write("## Source \n\n")
                new_file.write(source + "\n")
            new_file.write("\n")
            new_file.write(content[end + 3:])
    except Exception as e:
        print(f"Error in file: {os.path.basename(file_name).replace('.md','')}", str(e)[:25])
        continue
