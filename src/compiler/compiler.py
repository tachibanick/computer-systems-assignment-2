# Read file from path in 1st arg

import sys

op_code_map = {
    "ADD": "000000",
    "SUB": "000001",
    "MULT": "000010",
    "ADDI": "001000",  # Add immediate
    "SUBI": "001001",  # Subtract immediate
    "LD": "010000",  # Load data
    "SD": "010001",  # Store data
    "LI": "011000",  # Load immediate
    "JUMP": "100000",  # Jump
    "JEQ": "100001",  # Jump if equal
    "END": "110000",
}


def read_file(file_path):
    try:
        with open(file_path, "r") as file:
            return file.read()
    except FileNotFoundError:
        print(f"File not found: {file_path}")
        sys.exit(1)


def reg(register: str):
    """
    Convert register to 4-bit binary. First character is always R
    """
    return format(int(register[1:]), "04b")


def imm(imm: str):
    """
    Convert immediate value to 16-bit binary
    """
    return format(int(imm), "016b")


def unused(bits: int):
    return "0" * bits


def parse_line(line: str):
    match line.split():
        case ["ADD", dst, src1, src2]:
            return f"{op_code_map['ADD']}{reg(dst)} {reg(src1)} {reg(src2)}{unused(14)}"
        case ["SUB", dst, src1, src2]:
            return f"{op_code_map['SUB']}{reg(dst)} {reg(src1)} {reg(src2)}{unused(14)}"
        case ["MULT", dst, src1, src2]:
            return f"{op_code_map['MULT']}{reg(dst)}{reg(src1)} {reg(src2)}{unused(14)}"
        case ["ADDI", dst, src1, im]:
            return f"{op_code_map['ADDI']} {reg(dst)} {reg(src1)} {unused(2)} {imm(im)}"
        case ["SUBI", dst, src1, im]:
            return f"{op_code_map['SUBI']} {reg(dst)} {reg(src1)} {unused(2)} {imm(im)}"
        case ["LD", dst, addr]:
            return f"{op_code_map['LD']}{reg(dst)}{unused(4)} {reg(addr)} {unused(14)}"
        case ["SD", src, addr]:
            return f"{op_code_map['SD']}{unused(4)}{reg(src)} {reg(addr)} {unused(14)}"
        case ["LI", dst, im]:
            return f"{op_code_map['LI']} {reg(dst)} {unused(6)} {imm(im)}"
        case ["JUMP", addr]:
            return f"{op_code_map['JUMP']} {unused(10)} {imm(addr)}"
        case ["JEQ", addr, src1, src2]:
            return f"{op_code_map['JEQ']} {reg(src1)} {reg(src2)}{unused(2)}{imm(addr)}"
        case ["END"]:
            return f"{op_code_map['END']} {unused(26)}"

    return line


def main():
    if len(sys.argv) != 2:
        print("Usage: compiler.py <file>")
        sys.exit(1)

    file_path = sys.argv[1]
    file_contents = read_file(file_path)

    lines = file_contents.split("\n")

    # strip and remove empty lines and everything after ; in each line
    lines = [line.split(";")[0].strip() for line in lines if len(line)]

    # parse lines with parse_line
    lines = [parse_line(line).replace(" ", "") for line in lines]

    # Print lines where length is not 32
    for i, line in enumerate(lines):
        if len(line) != 32:
            print(f"Line {i+1} has length {len(line)}")

    print("\n".join(lines))
    print()

    # Convert each binary string to 8 digit hex
    hex_lines = [hex(int(line, 2))[2:].zfill(8) for line in lines]
    print("\"", end="")
    print("\".U(32.W),\n\"".join(hex_lines))
    print("\".U(32.W)")


if __name__ == "__main__":
    main()
