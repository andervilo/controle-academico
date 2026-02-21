import os
import glob

base_path = r"c:\projetos\sdd\controle-academico\src\main\java\br\com\sdd\controleacademico"

# Recursively find all java files
java_files = glob.glob(os.path.join(base_path, "**/*.java"), recursive=True)

for file_path in java_files:
    # Read content
    with open(file_path, "r", encoding="utf-8") as f:
        content = f.read()
        
    # Replace contents
    new_content = content.replace("ResponsavelFinanceiro", "Responsavel")
    new_content = new_content.replace("responsavelFinanceiro", "responsavel")
    new_content = new_content.replace("responsavel_financeiro", "responsavel")
    
    if content != new_content:
        with open(file_path, "w", encoding="utf-8") as f:
            f.write(new_content)

    # If the file name contains ResponsavelFinanceiro, rename it
    file_name = os.path.basename(file_path)
    if "ResponsavelFinanceiro" in file_name:
        new_file_name = file_name.replace("ResponsavelFinanceiro", "Responsavel")
        new_file_path = os.path.join(os.path.dirname(file_path), new_file_name)
        os.rename(file_path, new_file_path)
        print(f"Renamed {file_name} to {new_file_name}")
