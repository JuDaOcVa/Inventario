export interface LoginResponse {
    message: string;
    data: {
      name: string;
      email: string;
      phone: string;
      token: string;
    };
    status: number;
  }