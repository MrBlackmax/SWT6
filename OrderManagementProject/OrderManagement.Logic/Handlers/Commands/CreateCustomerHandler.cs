using AutoMapper;
using MediatR;
using OrderManagement.Dal;
using OrderManagement.Domain;
using OrderManagement.Dtos;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace OrderManagement.Logic.Handlers.Commands
{
    public class CreateCustomerHandler : IRequestHandler<CreateCustomerCommand, CustomerDto>
    {
        private readonly OrderManagementContext db;
        private readonly IMapper mapper;

        public CreateCustomerHandler(OrderManagementContext db, IMapper mapper)
        {
            this.db = db;
            this.mapper = mapper;
        }

        public Task<CustomerDto> Handle(CreateCustomerCommand request, CancellationToken cancellationToken)
        {
            Customer customer = mapper.Map<Customer>(request.Customer);
            db.Customers.Add(customer);
            db.SaveChanges();
            return Task.FromResult(mapper.Map<CustomerDto>(customer));
        }
    }
}
