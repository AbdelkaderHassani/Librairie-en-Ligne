export interface Product {
    id: string;
    userId: string;
    categoryId: string;
    title: string;
    quantity:number;
    description: string; 
    price: string;
    usbn: string;
    auteur: string; 
    datearrivage: Date; 
    productImg?: string;
    createdAt?: Date;
    updatedAt?: Date;
}
