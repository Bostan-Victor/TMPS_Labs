import json

class Product:
    def __init__(self, name: str, price: float):
        self.name = name
        self.price = price

class ProductManager:
    def __init__(self):
        self.products = []

    def add_product(self, product: Product):
        self.products.append(product)

    def get_products(self):
        return self.products

class ReportGenerator:
    def generate(self, products):
        raise NotImplementedError("Subclasses should implement this!")

class TextReportGenerator(ReportGenerator):
    def generate(self, products):
        report = "Product Report (Text Format)\n"
        report += "-----------------------------\n"
        for product in products:
            report += f"Product: {product.name}, Price: {product.price}\n"
        return report

class JSONReportGenerator(ReportGenerator):
    def generate(self, products):
        products_data = [{"name": product.name, "price": product.price} for product in products]
        return json.dumps(products_data, indent=4)

if __name__ == "__main__":
    product1 = Product("Laptop", 1200.00)
    product2 = Product("Smartphone", 800.00)
    
    manager = ProductManager()
    manager.add_product(product1)
    manager.add_product(product2)
    
    text_report = TextReportGenerator()
    print(text_report.generate(manager.get_products()))
    
    json_report = JSONReportGenerator()
    print(json_report.generate(manager.get_products()))
