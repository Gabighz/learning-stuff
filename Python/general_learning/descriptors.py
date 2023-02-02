# Hypothetical scenario where we want to validate data before it is serialized into a database

class BaseValidator:

    def __init__(self, lower_bound = None, upper_bound = None):
        self.lower_bound = lower_bound
        self.upper_bound = upper_bound

    def __set_name__(self, owner_class, property_name):
        self.property_name = property_name

    def __get__(self, instance, owner_class):
        if instance is None:
            return self

        return instance.__dict__.get(self.property_name, None)

    def validate(self, value): ...

    def __set__(self, instance, value):
        self.validate(value)
        instance.__dict__[self.property_name] = value

class CharField(BaseValidator):

    def __init__(self, lower_bound, upper_bound):
        super().__init__(max(lower_bound, 0), upper_bound)

    def validate(self, instance, value):
        if not isinstance(value, str):
            raise ValueError(f'{self.property_name} must be a String!')

        if not (self.lower_bound <= len(value) <= self.upper_bound):
            raise ValueError(f'{self.property_name} is out of bounds!')

class IntegerField(BaseValidator):

    def validate(self, instance, value):
        if not isinstance(value, int):
            raise ValueError(f'{self.property_name} must be an Integer!')

        if not (self.lower_bound <= value <= self.upper_bound):
            raise ValueError(f'{self.property_name} is out of bounds!')

class Person:
    name = CharField(1, 50)
    age = IntegerField(0, 200)