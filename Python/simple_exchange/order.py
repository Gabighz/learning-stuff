class Order:
    count = 0
    
    @classmethod
    def get_next_count(cls):
        # NOTE: could've just incremented 'count' in the initializer, 
        # but using this method can make the code more readable and easier to modify 
        # if the ID generation logic needs to change
        cls.count += 1
        return cls.count

    def __init__(self, *args):
        self.type, self.num_contracts, self.limit_price, self.currency = args
        self.id = f'{self.type}_{Order.get_next_count()}'
        self.remaining_contracts = self.num_contracts
        self.status = 'unfilled'
        self.matches = []