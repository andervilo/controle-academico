import os, glob

def replace_in_files():
    files = glob.glob('src/main/java/**/*.java', recursive=True)
    for f in files:
        if not os.path.isfile(f): continue
        with open(f, 'r', encoding='utf-8') as r:
            content = r.read()
        
        if 'ResponsavelFinanceiro' in content or 'responsavelFinanceiro' in content:
            new_content = content.replace('ResponsavelFinanceiro', 'Responsavel').replace('responsavelFinanceiro', 'responsavel')
            # Fix plurals properly
            new_content = new_content.replace('ResponsaveisFinanceiro', 'Responsaveis')
            new_content = new_content.replace('ResponsaveisFinanceiros', 'Responsaveis')
            with open(f, 'w', encoding='utf-8') as w:
                w.write(new_content)

def rename_files():
    files = glob.glob('src/main/java/**/*ResponsavelFinanceiro*.java', recursive=True)
    for f in files:
        new_f = f.replace('ResponsavelFinanceiro', 'Responsavel')
        os.rename(f, new_f)
        
if __name__ == '__main__':
    replace_in_files()
    rename_files()
